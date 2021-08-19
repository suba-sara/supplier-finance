import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignUpSupplierComponent } from './sign-up-supplier.component';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { PersonalDataFormComponent } from './personal-data-form/personal-data-form.component';
import { BankAccountDetailsFormComponent } from './bank-account-details-form/bank-account-details-form.component';
import { SignUpClientComponent } from './sign-up-client.component';
import { SignUpComponent } from './sign-up.component';
import { UserDataFormComponent } from './user-data-form/user-data-form.component';
import { UserTypeButtonComponent } from './user-type-button/user-type-button.component';
import { FormHeaderComponent } from './form-header/form-header.component';
import { SuccessFeedbackComponent } from './success-feedback/success-feedback.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  declarations: [
    SignUpComponent,
    SignUpClientComponent,
    SignUpSupplierComponent,
    PersonalDataFormComponent,
    BankAccountDetailsFormComponent,
    UserDataFormComponent,
    UserTypeButtonComponent,
    FormHeaderComponent,
    SuccessFeedbackComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  exports: [SuccessFeedbackComponent],
})
export class SignUpModule {}
