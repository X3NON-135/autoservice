import {Component} from '@angular/core';
import {AutoOwner} from "../models/owner";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Location} from "@angular/common";
import {Order} from "../models/order";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-owners',
  templateUrl: './owners.component.html',
  styleUrls: ['./owners.component.css']
})
export class OwnersComponent {
  private url = 'http://localhost:8080/owners';
  owners: Array<AutoOwner> = [];
  orders: Array<Order> = [];

  constructor(
    private http: HttpClient,
    private location: Location
  ) {
  }

  reactiveForm = new FormGroup({
    id: new FormControl(0),
    fullName: new FormControl(''),
  })

  idForm = new FormGroup({
    id: new FormControl(0),
  })

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  save() {
    let id = this.reactiveForm.controls.id.value;
    let fullName = this.reactiveForm.controls.fullName.value;
    if (!fullName) {
      return;
    }
    fullName = fullName.trim();
    if (!id) {
      this.createOwner({fullName} as AutoOwner)
        .subscribe(owner => {
          this.owners.push(owner);
        });
    } else {
      this.updateOwner({id, fullName} as AutoOwner)
        .subscribe(owner => {
          this.owners.push(owner);
        });
    }
  }

  private createOwner(owner: AutoOwner): Observable<AutoOwner> {
    return this.http.post<AutoOwner>(this.url, owner, this.httpOptions)
  }

  private updateOwner(owner: AutoOwner): Observable<AutoOwner> {
    return this.http.put<AutoOwner>(this.url + '/' + owner.id, owner, this.httpOptions);
  }

  getAllOrderById() {
    let id = this.idForm.controls.id.value;
    this.http.get<Array<Order>>(this.url + '/' + id + '/orders')
      .subscribe(responseOrders => this.orders = responseOrders);
  }
}
