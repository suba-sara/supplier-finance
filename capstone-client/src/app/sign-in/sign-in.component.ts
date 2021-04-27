import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

const {API_PATH} = environment;

export type Login = {
  username: string;
  password: string;
};

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})

export class SignInComponent implements OnInit {
  @Input() login: Login;

  constructor(private http: HttpClient) {
    this.login = {
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
  }

  signIn(): void {
    this.http.post(`${API_PATH}/sign-in`, this.login)
      .subscribe((response) => console.log(response));
  }
}
