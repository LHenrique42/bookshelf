import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialModule } from '@app/material.module';
import { BookListRoutingModule } from './book-list-routing.module';
import { BookListComponent } from './book-list.component';
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatCardModule } from "@angular/material/card";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";
import { MaterialElevationDirective } from './book-list-elevation.directive';
@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatToolbarModule,
    MatButtonModule,
    FlexLayoutModule,
    CommonModule, TranslateModule, FlexLayoutModule, MaterialModule, BookListRoutingModule],
  declarations: [BookListComponent, MaterialElevationDirective],
})
export class BookListModule {}
