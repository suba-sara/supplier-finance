import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvoiceUploadComponent } from './invoice-upload/invoice-upload.component';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [InvoiceUploadComponent],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
  ],
})
export class InvoiceModule {}
