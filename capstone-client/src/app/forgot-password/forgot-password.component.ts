import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {ForgotPasswordService} from './forgot-password-service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  userAccountForm: FormGroup = new FormGroup({
    userId: new FormControl('', Validators.required)
  });

  errorMessages = '';
  isAccountHas = false;

  constructor(private forgotPasswordService: ForgotPasswordService) {
  }

  ngOnInit(): void {
  }

  get userId(): AbstractControl | null {
    return this.userAccountForm.get('userId');
  }

  searchAccount(): void {
    this.forgotPasswordService.searchAccount(this.userAccountForm.value).subscribe(res => {
      if (res) {
        this.isAccountHas = !this.isAccountHas;
        this.userAccountForm?.addControl(
          'OTP',
          new FormControl('', [
            Validators.required,
            Validators.maxLength(6),
            Validators.minLength(6),
            Validators.pattern(/^-?(0|[1-9]\d*)?$/),
          ])
        );

        this.userAccountForm?.addControl('password', new FormControl('', [Validators.required, Validators.minLength(6)]));
        this.userAccountForm?.addControl('confirm_password', new FormControl('', [Validators.required, Validators.minLength(6)]));
      }
    }, err => this.errorMessages = err);
  }

  onSubmitButtonClick(): void {
    this.userAccountForm.get('confirm_password')?.disable();
    this.forgotPasswordService.verifyUser(this.userAccountForm.value).subscribe(res => {
      if (res) {
        this.isAccountHas = !this.isAccountHas;
      }
    }, err => this.errorMessages = err);
  }
}
