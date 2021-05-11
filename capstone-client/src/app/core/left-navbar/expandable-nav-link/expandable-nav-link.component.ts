import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-expandable-nav-link',
  templateUrl: './expandable-nav-link.component.html',
  styleUrls: ['./expandable-nav-link.component.scss'],
})
export class ExpandableNavLinkComponent implements OnInit {
  isExpanded: boolean;

  @Input()
  title!: string;

  constructor() {
    this.isExpanded = false;
  }

  ngOnInit(): void {}

  toggleExpandedMode(): void {
    this.isExpanded = !this.isExpanded;
  }
}
