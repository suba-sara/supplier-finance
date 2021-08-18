import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ForgotPasswordComponent } from './forgot-password.component';
import {ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';

@NgModule({
  declarations: [ForgotPasswordComponent],
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule],
})
export class ForgotPasswordModule {}
