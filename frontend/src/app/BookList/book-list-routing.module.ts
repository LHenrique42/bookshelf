import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { marker } from '@biesbjerg/ngx-translate-extract-marker';

import { Shell } from '@app/shell/shell.service';
import { BookListComponent } from './book-list.component';

const routes: Routes = [
  Shell.childRoutes([{ path: 'list', component: BookListComponent, data: { title: marker('BookList') } }]),
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [],
})
export class BookListRoutingModule {}
