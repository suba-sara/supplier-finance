import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

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

@Component({
  selector: 'app-personal-data-form',
  templateUrl: './personal-data-form.component.html',
  styleUrls: ['./personal-data-form.component.scss'],
})
export class PersonalDataFormComponent implements OnInit {
  @Input()
  goBack!: () => void;

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

  @Output()
  formSubmitEvent = new EventEmitter<PersonalDetails>();

  ngOnInit(): void {}

  confirmPersonalData(): void {
    if (this.personalDataForm.valid) {
      this.formSubmitEvent.emit(this.personalDataForm.value);
    } else {
      this.personalDataForm.markAsDirty();
    }
  }
}
