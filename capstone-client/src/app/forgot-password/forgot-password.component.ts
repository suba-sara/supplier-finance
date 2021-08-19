import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ForgotPasswordService } from './forgot-password-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit {
  userAccountForm: FormGroup = new FormGroup({
    userId: new FormControl('', Validators.required),
  });

  errorMessages = '';
  isAccountHas = false;
  getOtpLoading = false;
  otpMessage: string | undefined = '';
  confirmPasswordMessage = false;
  successfull = false;

  constructor(
    private forgotPasswordService: ForgotPasswordService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  get userId(): AbstractControl | null {
    return this.userAccountForm.get('userId');
  }

  searchAccount(): void {
    this.errorMessages = '';
    this.otpMessage = '';
    this.getOtpLoading = !this.getOtpLoading;
    this.forgotPasswordService
      .searchAccount(this.userId?.value)
      .subscribe(
        (res) => {
          if (res.valid) {
            this.otpMessage = res.message;
            this.isAccountHas = !this.isAccountHas;
            this.userAccountForm?.addControl(
              'otp',
              new FormControl('', [
                Validators.required,
                Validators.pattern(/[\d]/),
              ])
            );

            this.userAccountForm?.addControl(
              'password',
              new FormControl('', [Validators.required])
            );
            this.userAccountForm?.addControl(
              'confirm_password',
              new FormControl('', [Validators.required])
            );
          }
        },
        (err) => {
          this.errorMessages = 'User not found!';
        }
      )
      .add(() => (this.getOtpLoading = !this.getOtpLoading));
  }

  get otp(): AbstractControl | null {
    return this.userAccountForm.get('otp');
  }

  onSubmitButtonClick(): void {
    this.confirmPasswordMessage = false;
    this.successfull = false;
    if (
      this.userAccountForm.get('confirm_password')?.value ===
      this.userAccountForm.get('password')?.value
    ) {
      this.userAccountForm.get('confirm_password')?.disable();
      this.forgotPasswordService
        .verifyUser(this.userAccountForm.value)
        .subscribe(
          (res) => {
            if (res.valid) {
              this.isAccountHas = !this.isAccountHas;
              this.successfull = !this.successfull;
              // this.router.navigateByUrl('/sign-in');
            }
          },
          (err) => {
            this.errorMessages =
              err === 'User not found.' ? 'Invalid OTP Code' : err;
          }
        );
      this.userAccountForm.get('confirm_password')?.enable();
    } else {
      this.confirmPasswordMessage = !this.confirmPasswordMessage;
    }
  }
}
