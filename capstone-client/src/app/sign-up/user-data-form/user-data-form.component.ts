import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';

export type UserDetails = {
  username: string;
  password: string;
};

@Component({
  selector: 'app-user-data-form',
  templateUrl: './user-data-form.component.html',
  styleUrls: ['./user-data-form.component.scss'],
})
export class UserDataFormComponent implements OnInit {
  userDataForm = new FormGroup(
    {
      username: new FormControl('', [
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

  @Output()
  formSubmitEvent = new EventEmitter<UserDetails>();

  ngOnInit(): void {}

  confirmUserData(): void {
    if (this.userDataForm.valid) {
      this.formSubmitEvent.emit(this.userDataForm.value);
    } else {
      this.userDataForm.markAsDirty();
    }
  }
}
