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
    MatTableModule,
    MatTooltipModule,
    MatPaginatorModule,
  ],
})
export class InvoiceModule {}
