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

@NgModule({
  declarations: [
    SignUpComponent,
    SignUpClientComponent,
    SignUpSupplierComponent,
    PersonalDataFormComponent,
    BankAccountDetailsFormComponent,
    UserDataFormComponent,
    UserTypeButtonComponent,
  ],
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
  ],
})
export class SignUpModule {}
