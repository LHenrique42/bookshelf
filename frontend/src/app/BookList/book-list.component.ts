import { Component, OnInit } from '@angular/core';

import { environment } from '@env/environment';
import { BookListService } from './book-list.service';

import { Book } from '../models/book.model';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss'],
})
export class BookListComponent implements OnInit {
  version: string | null = environment.version;
  title = 'Bookshelf';
  gridColumns = 3;
  defaultElevation = 2;
  raisedElevation = 8;

  book = {} as Book;
  books: Book[];

  constructor(private bookListService: BookListService) {}

  ngOnInit() {
      this.getBookList();
  }

  getBookList() {
    this.bookListService.getBookList().subscribe(
      (books: Book[] | any) => {
        this.books = books;
      },
      (err) => { console.error(err) }
    )
  }

  toggleGridColumns() {
    this.gridColumns = this.gridColumns === 3 ? 4 : 3;
  }
}
