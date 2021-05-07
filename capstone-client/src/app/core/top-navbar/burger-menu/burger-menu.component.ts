import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-burer-menu',
  templateUrl: './burger-menu.component.html',
  styleUrls: ['./burger-menu.component.scss'],
})
export class BurgerMenuComponent implements OnInit {
  @Input()
  toggleMenu!: (hide?: boolean) => void;

  constructor() {}

  ngOnInit(): void {}
}
