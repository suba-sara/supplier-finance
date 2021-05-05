import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignUpSupplierComponent } from './sign-up-supplier.component';
import { SignUpClientComponent } from './sign-up-client.component';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { PersonalDataFormComponent } from './personal-data-form/personal-data-form.component';

@NgModule({
  declarations: [
    SignUpSupplierComponent,
    SignUpClientComponent,
    PersonalDataFormComponent,
  ],
  imports: [CommonModule, MatInputModule, MatButtonModule, ReactiveFormsModule],
})
export class SignUpModule {}
