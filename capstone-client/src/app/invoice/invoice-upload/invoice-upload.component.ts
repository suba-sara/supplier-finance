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
          if (dayjs(control.value).isBefore(dayjs().startOf('day'))) {
            return {
              invalid: true,
            };
          }

          return null;
        },
      ],
    }),
    invoiceTitle: new FormControl('', { validators: [Validators.required] }),
    invoiceAmount: new FormControl('', {
      validators: [Validators.required, Validators.min(0)],
    }),
    currency: new FormControl('USD', { validators: [Validators.required] }),
    invoiceFile: new FormControl('', { validators: [Validators.required] }),
  });

  constructor(private invoiceUploadService: InvoiceUploadService) {}

  ngOnInit(): void {
    this.invoiceUploadService.fetchMyClientId().subscribe(
      (res) => {
        this.clientId = res.clientId;
      },
      (err) => {
        console.log(err);
      }
    );
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
          console.log(error);
        }
      );
  }

  validateFile(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files) {
      const { name, size } = target.files[0];
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

  createInvoice(): void {
    this.uploadInvoiceForm.markAllAsTouched();
    console.log('😂');

    if (!this.uploadInvoiceForm.errors) {
      this.invoiceUploadService
        .uploadInvoice(this.uploadInvoiceForm.value)
        .subscribe(
          (res) => {
            console.log(res);
          },
          (error) => {
            console.log(error);
          }
        );
    }
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
          console.log(error);
        }
      );
  }

  validateFile(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files) {
      const { name, size } = target.files[0];
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

  createInvoice(): void {
    this.uploadInvoiceForm.markAllAsTouched();
    console.log('😂');

    if (!this.uploadInvoiceForm.errors) {
      this.invoiceUploadService
        .uploadInvoice(this.uploadInvoiceForm.value)
        .subscribe(
          (res) => {
            console.log(res);
          },
          (error) => {
            console.log(error);
          }
        );
    }
  }
}
