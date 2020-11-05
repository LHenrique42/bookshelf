import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { MaterialModule } from '@app/material.module';
import { BookRegisterRoutingModule } from './book-register-routing.module';
import { BookRegisterComponent } from './book-register.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    ReactiveFormsModule,
    CommonModule, TranslateModule, FlexLayoutModule, MaterialModule, BookRegisterRoutingModule],
  declarations: [BookRegisterComponent],
})
export class BookRegisterModule {}
