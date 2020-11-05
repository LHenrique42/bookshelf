import { Author } from './author.model';
import { Category } from './category.model';

export interface Book {
  id: Number,
  title: String,
  author: Author,
  isbn: String,
  coverImg: String,
  publisher: String,
  dateOfPublication: String,
  description: String,
  numberOfPages: Number,
  category: Category;
}
