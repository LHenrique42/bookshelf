import { Component, OnInit } from '@angular/core';

import { environment } from '@env/environment';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss'],
})
export class AboutComponent implements OnInit {
  version: string | null = environment.version;
  title = 'Bookshelf';
  gridColumns = 3;
  defaultElevation = 2;
  raisedElevation = 8;

  constructor() {}

  toggleGridColumns() {
    this.gridColumns = this.gridColumns === 3 ? 4 : 3;
  }

  ngOnInit() {}

}
