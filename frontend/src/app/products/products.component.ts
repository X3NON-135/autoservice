import {Component} from '@angular/core';
import {Product} from "../models/product";
import {Observable} from "rxjs";
import {Location} from '@angular/common';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {
  products: Array<Product> = []

  partForm = new FormGroup({
    id: new FormControl(0),
    title: new FormControl(''),
    price: new FormControl(0),
  })

  private url = 'http://localhost:8080/products';

  constructor(
    private http: HttpClient,
    private location: Location
  ) {
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  save(): void {
    let id = this.partForm.controls.id.value;
    let title = this.partForm.controls.title.value;
    let price = this.partForm.controls.price.value;
    if (!title || !price) {
      return;
    }
    title = title.trim();
    if (!id) {
      this.createPart({title, price} as Product)
        .subscribe(part => {
          this.products.push(part)
        });
    } else {
      this.updatePart({id, title, price} as Product)
        .subscribe(part => {
          this.products.push(part)
        });
    }
  }

  createPart(product: Product): Observable<Product> {
    return this.http.post<Product>(this.url, product, this.httpOptions).pipe();
  }

  updatePart(product: Product): Observable<Product> {
    return this.http.put<Product>(this.url + '/' + product.id, product, this.httpOptions).pipe();
  }
}
