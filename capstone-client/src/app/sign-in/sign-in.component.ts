import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService, SignInData} from '../auth/auth.service';
import {Router} from '@angular/router';

export type SignInResponseType = {
  jwt: string,
  userType: string,
};

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})

export class SignInComponent implements OnInit {
  signInData: SignInData;
  isPasswordVisible: boolean;
  errorMessage?: string;

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) {
    this.signInData = {
      username: '',
      password: ''
    };
    this.isPasswordVisible = true;
  }

  ngOnInit(): void {
  }

  handleSignInClick(): void {
    this.errorMessage = undefined;
    this.authService.signIn(this.signInData, (res) => {
      if (res.status === 200) {
        this.router.navigate(['dashboard']);
      } else if (res.status === 403) {
        this.errorMessage = res.message;
      }
    });
  }
}
