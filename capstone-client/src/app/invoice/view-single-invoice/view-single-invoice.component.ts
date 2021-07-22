import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ViewSingleInvoicesService} from './view-single-invoice.service';
import {Invoice} from '../invoice.types';
import {environment} from '../../../environments/environment';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {InvoiceUploadService} from '../invoice-upload/invoice-upload.service';
import {SnackbarService} from '../../util/snakbar.service';
import {AuthService} from '../../core/auth/auth.service';

@Component({
  selector: 'app-view-single-invoice',
  templateUrl: './view-single-invoice.component.html',
  styleUrls: ['./view-single-invoice.component.scss'],
})
export class ViewSingleInvoiceComponent implements OnInit {
  SERVER = environment.SERVER;
  invoice?: Invoice = {
    invoiceId: 0,
    client: {
      clientId: '',
      name: '',
    },
    supplier: {
      supplierId: '',
      name: '',
    },
    invoiceNumber: 0,
    uploadedDate: '',
    invoiceDate: '',
    amount: 0,
    status: '',
    currencyType: 'USD',
    fileUrl: ''
  };
  edit: boolean;
  userTypeApiPath = 'SUPPLIER';

  constructor(
    private _router: Router,
    private route: ActivatedRoute,
    private viewSingleInvoicesService: ViewSingleInvoicesService,
    private fb: FormBuilder,
    private invoiceUploadService: InvoiceUploadService,
    private snackBarService: SnackbarService,
    private authService: AuthService
  ) {
    const invoiceId = this.route.snapshot.params['id'];
    this.edit = this.route.snapshot.params['edit'] === 'edit';
    this.viewSingleInvoicesService
      .getInvoiceById(invoiceId)
      .then((invoice) => (this.invoice = invoice));
    authService.user$.subscribe((res: any) => {
      this.userTypeApiPath = res.userType;
    });
  }

  invoiceForm = this.fb.group({
    status: ['', Validators.required],
    amount: ['', [Validators.required, Validators.min(0), Validators.pattern('\\^([\\\\d]{0,4})(\\\\.|$)([\\\\d]{2,2}|)$')]],
    currencyType: new FormControl(this.invoice?.currencyType, {validators: [Validators.required]}),
  });

  ngOnInit(): void {
  }

  async onClickSave(): Promise<any> {
    this.invoiceForm.markAllAsTouched();
    console.log(this.invoiceForm.get('invoiceFile')?.value);
    if (!this.invoiceForm.errors) {
      await this.viewSingleInvoicesService.editInvoice(this.invoiceForm.value, this.userTypeApiPath)?.then((res) => {
        this.snackBarService.snackBar?.open(
          'Invoice Update Successfully',
          undefined,
          {
            duration: 2000,
          }
        );
        this._router.navigate([
          `invoice/view-invoice/${res.invoice.invoiceId}/view`,
        ]);
      }).catch(err => console.error(err));
    }
  }
}
