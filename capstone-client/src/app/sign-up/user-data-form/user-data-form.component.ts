import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

export type UserDetails = {
  username: string;
  password: string;
  confirm_password: string;
};

@Component({
  selector: 'app-user-data-form',
  templateUrl: './user-data-form.component.html',
  styleUrls: ['./user-data-form.component.scss'],
})
export class UserDataFormComponent implements OnInit {
  userDataForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
    confirm_password: new FormControl('', [Validators.required]),
  });

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
