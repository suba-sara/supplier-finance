import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SignUpService } from '../sign-up.service';

export type PersonalDetails = {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  addressLine1: string;
  city: string;
  state: string;
  province: string;
  country: string;
};

export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'app-personal-data-form',
  templateUrl: './personal-data-form.component.html',
  styleUrls: ['./personal-data-form.component.scss'],
})
export class PersonalDataFormComponent implements OnInit {
  @Input()
  goBack!: () => void;

  @Input()
  initialValues!: PersonalDetails;

  personalDataForm = new FormGroup({
    firstName: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(30),
    ]),
    lastName: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(30),
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.email,
      Validators.maxLength(255),
    ]),
    phone: new FormControl('', [
      Validators.required,
      Validators.pattern(/^[0-9+ ]+$/),
    ]),
    addressLine1: new FormControl('', [
      Validators.required,
      Validators.maxLength(500),
    ]),
    city: new FormControl('', [Validators.required, Validators.maxLength(255)]),
    state: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    province: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    country: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
  });

  constructor(private signUpService: SignUpService) {}

  @Output()
  formSubmitEvent = new EventEmitter<PersonalDetails>();

  ngOnInit(): void {
    if (this.initialValues) {
      this.personalDataForm.controls['firstName'].setValue(
        this.initialValues.firstName
      );
      this.personalDataForm.controls['lastName'].setValue(
        this.initialValues.lastName
      );
      this.personalDataForm.controls['email'].setValue(
        this.initialValues.email
      );
      this.personalDataForm.controls['phone'].setValue(
        this.initialValues.phone
      );
      this.personalDataForm.controls['addressLine1'].setValue(
        this.initialValues.addressLine1
      );
      this.personalDataForm.controls['city'].setValue(this.initialValues.city);
      this.personalDataForm.controls['state'].setValue(
        this.initialValues.state
      );
      this.personalDataForm.controls['province'].setValue(
        this.initialValues.province
      );
      this.personalDataForm.controls['country'].setValue(
        this.initialValues.country
      );
    }
  }

  confirmPersonalData(): void {
    if (this.personalDataForm.valid) {
      this.signUpService
        .checkEmail(this.personalDataForm.value.email)
        .subscribe((value) => {
          if (value.valid) {
            this.formSubmitEvent.emit(this.personalDataForm.value);
          } else {
            this.personalDataForm.controls['email'].setErrors({ exists: true });
          }
        });
    } else {
      this.personalDataForm.markAsDirty();
    }
  }
}
