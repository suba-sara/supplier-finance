import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvoiceUploadComponent } from './invoice-upload/invoice-upload.component';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientModule } from '@angular/common/http';
import { ViewInvoicesComponent } from './view-invoices/view-invoices.component';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ViewSingleInvoiceComponent } from './view-single-invoice/view-single-invoice.component';
import { InvoiceNumberFormatPipe } from './pipes/invoice-number-format.pipe';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { InvoiceAgePipe } from './pipes/invoice-age.pipe';
import { MatSortModule } from '@angular/material/sort';
import { RouterModule } from '@angular/router';
import { MatDialogModule } from '@angular/material/dialog';
import { ConfirmDialogModule } from '../shared/confirm-dialog/confirm-dialog.module';

@NgModule({
  declarations: [
    InvoiceUploadComponent,
    ViewInvoicesComponent,
    ViewSingleInvoiceComponent,
    InvoiceNumberFormatPipe,
    DateFormatPipe,
    InvoiceAgePipe,
  ],
  imports: [
    CommonModule,
    SharedModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatButtonModule,
    MatTableModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatSortModule,
    RouterModule,
    MatDialogModule,
    ConfirmDialogModule,
  ],
})
export class InvoiceModule {}
