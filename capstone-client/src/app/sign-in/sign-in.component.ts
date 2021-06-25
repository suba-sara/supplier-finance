import { Component, OnInit } from '@angular/core';
import { AuthService, SignInData } from '../core/auth/auth.service';
import { Router } from '@angular/router';

export type SignInResponseType = {
  jwt: string;
  userType: string;
};

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  signInData: SignInData;
  isPasswordVisible: boolean;
  errorMessage?: string;

  constructor(private authService: AuthService, private router: Router) {
    this.signInData = {
      userId: '',
      password: '',
    };
    this.isPasswordVisible = true;
  }

  ngOnInit(): void {}

  handleSignInClick(): void {
    this.errorMessage = undefined;
    this.authService.signIn(this.signInData).subscribe(
      () => {
        this.router.navigateByUrl('/dashboard');
      },
      (error) => {
        this.errorMessage = error;
      }
    );
  }
}
