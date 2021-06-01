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

@NgModule({
  declarations: [InvoiceUploadComponent, ViewInvoicesComponent],
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
  ],
})
export class InvoiceModule {}
