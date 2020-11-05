import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { BookRegisterService } from './book-register.service';
import { Author } from '../models/author.model';
import { Category } from '../models/category.model';
import { Book } from '../models/book.model';

@Component({
  selector: 'app-book-register',
  templateUrl: './book-register.component.html',
  styleUrls: ['./book-register.component.scss'],
})
export class BookRegisterComponent implements OnInit {
  Categories: any = ['Fantasia', 'Ficção', 'Terror'];
  bookRegister: FormGroup;

  constructor(private _formBuilder: FormBuilder,
    private bookRegisterService: BookRegisterService) {
  }

  ngOnInit() {
    this.bookRegister = this._formBuilder.group({
      authorName: ['', Validators.required],
      authorBirthdayDate: ['', Validators.required],
      category: ['', Validators.required],
      bookName: ['', Validators.required],
      isbn: ['', Validators.required],
      publisher: ['', Validators.required],
      bookDateOfPublication: ['', Validators.required],
      numberOfPages: ['', Validators.required],
      coverImageUrl: ['', Validators.required],
      description: ['', Validators.required],
    })
  }
  onSubmit() {

  }
}
