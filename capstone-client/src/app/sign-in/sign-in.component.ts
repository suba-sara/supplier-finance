import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

const {API_PATH} = environment;

export type SignInData = {
  username: string;
  password: string;
};

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})

export class SignInComponent implements OnInit {
  @Input() signInData: SignInData;

  constructor(private http: HttpClient) {
    this.signInData = {
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
  }


  handleSignInClick(): void {
    this.http.post(`${API_PATH}/sign-in`, this.signInData)
      .subscribe((response) => console.log(response));
  }
}
