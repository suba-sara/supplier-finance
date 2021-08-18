import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { SignUpService } from '../sign-up.service';

export type UserDetails = {
  userId: string;
  password: string;
};

@Component({
  selector: 'app-user-data-form',
  templateUrl: './user-data-form.component.html',
  styleUrls: ['./user-data-form.component.scss'],
})
export class UserDataFormComponent implements OnInit {
  @Input()
  initialValues?: UserDetails;

  @Output()
  formSubmitEvent = new EventEmitter<UserDetails>();

  userDataForm: FormGroup;

  constructor(private signUpService: SignUpService) {
    this.userDataForm = new FormGroup(
      {
        userId: new FormControl('', [
          Validators.required,
          Validators.minLength(4),
        ]),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
        confirm_password: new FormControl(''),
      },
      {
        validators: (control) => {
          const errors: ValidationErrors = {};
          const passwordControl = control.get('password');
          const confirmPasswordControl = control.get('confirm_password');

          if (passwordControl?.value !== confirmPasswordControl?.value) {
            confirmPasswordControl?.setErrors({ notSame: true });
          }

          return errors;
        },
      }
    );
  }

  ngOnInit(): void {
    if (this.initialValues) {
      this.userDataForm.controls['userId'].setValue(this.initialValues.userId);
      this.userDataForm.controls['password'].setValue(
        this.initialValues.password
      );
      this.userDataForm.controls['confirm_password'].setValue(
        this.initialValues.password
      );
    }
  }

  confirmUserData(): void {
    if (this.userDataForm.valid) {
      this.signUpService
        .checkUsername(this.userDataForm.value.userId)
        .subscribe((value) => {
          if (value.valid) {
            this.formSubmitEvent.emit(this.userDataForm.value);
          } else {
            this.userDataForm.controls['userId'].setErrors({ exists: true });
          }
        });
    } else {
      this.userDataForm.markAsDirty();
    }
  }
}
