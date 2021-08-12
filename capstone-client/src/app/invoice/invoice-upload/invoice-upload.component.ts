import { Component, OnInit } from '@angular/core';
import * as dayjs from 'dayjs';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { InvoiceUploadService } from './invoice-upload.service';
import { Router } from '@angular/router';
import { SnackbarService } from '../../util/snakbar.service';
import { AppService } from '../../app.service';

@Component({
  selector: 'app-invoice-upload',
  templateUrl: './invoice-upload.component.html',
  styleUrls: ['./invoice-upload.component.scss'],
})
export class InvoiceUploadComponent implements OnInit {
  clientId = '';
  supplierIdIsValid = false;
  selectedFile:
    | undefined
    | {
        fileName: string;
        size: number; // size in kb
      } = undefined;

  uploadInvoiceForm = new FormGroup({
    supplierId: new FormControl('', { validators: [Validators.required] }),
    invoiceNumber: new FormControl('', { validators: [Validators.required] }),
    invoiceDate: new FormControl('', {
      validators: [
        Validators.required,
        (control: AbstractControl): ValidationErrors | null => {
          // check if the invoice date is a future value or not
          if (dayjs(control.value).isAfter(dayjs().startOf('day'))) {
            return {
              invalid: true,
            };
          }

          return null;
        },
      ],
    }),
    invoiceTitle: new FormControl('', { validators: [Validators.required] }),
    amount: new FormControl('', {
      validators: [Validators.required, Validators.min(0)],
    }),
    currencyType: new FormControl('USD', { validators: [Validators.required] }),
    invoiceFile: new FormControl('', { validators: [Validators.required] }),
    invoiceFileSrc: new FormControl('', { validators: [Validators.required] }),
  });

  constructor(
    private invoiceUploadService: InvoiceUploadService,
    private snackBarService: SnackbarService,
    private _router: Router,
    private appService: AppService
  ) {}

  ngOnInit(): void {
    this.invoiceUploadService.fetchMyClientId().subscribe(
      (res) => {
        this.clientId = res.clientId;
      },
      (err) => {
        console.log(err);
      }
    );

    this.appService.setPageTitle('Upload Invoice');
  }

  checkSupplierId(): void {
    this.supplierIdIsValid = false;
    const supplierIdControl = this.uploadInvoiceForm.get('supplierId');

    this.invoiceUploadService
      .checkSupplierId(supplierIdControl?.value)
      .subscribe(
        () => {
          supplierIdControl?.setErrors(null);
          this.supplierIdIsValid = true;
        },
        (error) => {
          if (!error.isValid) {
            supplierIdControl?.setErrors({
              ...supplierIdControl?.errors,
              invalid: true,
            });
            this.supplierIdIsValid = false;
          }
        }
      );
  }

  validateFile(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files) {
      const file = target.files[0];
      const { name, size } = file;
      this.uploadInvoiceForm.patchValue({
        invoiceFileSrc: file,
      });
      this.selectedFile = {
        fileName: name,
        size: Math.floor(size / 1024),
      };

      if (this.selectedFile.size > 1024) {
        this.uploadInvoiceForm.get('invoiceFile')?.setErrors({ size: true });
      } else {
        this.uploadInvoiceForm.get('invoiceFile')?.setErrors({});
      }
    } else {
      this.selectedFile = undefined;
    }
  }

  async createInvoice(): Promise<void> {
    this.uploadInvoiceForm.markAllAsTouched();

    if (!this.uploadInvoiceForm.errors) {
      this.invoiceUploadService
        .createInvoice(this.uploadInvoiceForm.value)
        .then((res) => {
          this.snackBarService.snackBar?.open(
            'Invoice Uploaded Successfully',
            undefined,
            {
              duration: 2000,
            }
          );
          this._router.navigate([
            `invoice/view-invoice/${res.invoice.invoiceId}`,
          ]);
        })
        .catch((e) => console.log(e));
    }
  }
}
