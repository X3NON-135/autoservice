import { NgModule } from '@angular/core';
import {OrdersComponent} from "./orders/orders.component";
import {RouterModule, Routes} from '@angular/router';
import {OwnersComponent} from "./owners/owners.component";
import {DutiesComponent} from "./duties/duties.component";
import {ProductsComponent} from "./products/products.component";
import {MastersComponent} from "./masters/masters.component";
import {AutosComponent} from "./autos/autos.component";

const routes: Routes = [{
  path: 'auto_service',
  children: [
    {
      path: 'orders',
      component: OrdersComponent
    },
    {
      path: 'owners',
      component: OwnersComponent
    },
    {
      path: 'duties',
      component: DutiesComponent
    },
    {
      path: 'products',
      component: ProductsComponent
    },
    {
      path: 'masters',
      component: MastersComponent
    },
    {
      path: 'autos',
      component: AutosComponent
    }
  ]
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
