import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BalanceWheelComponent } from './balance-wheel.component';
import { BalanceWheelDetailComponent } from './balance-wheel-detail.component';
import { BalanceWheelPopupComponent } from './balance-wheel-dialog.component';
import { BalanceWheelDeletePopupComponent } from './balance-wheel-delete-dialog.component';

export const balanceWheelRoute: Routes = [
    {
        path: 'balance-wheel',
        component: BalanceWheelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.balanceWheel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'balance-wheel/:id',
        component: BalanceWheelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.balanceWheel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const balanceWheelPopupRoute: Routes = [
    {
        path: 'balance-wheel-new',
        component: BalanceWheelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.balanceWheel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'balance-wheel/:id/edit',
        component: BalanceWheelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.balanceWheel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'balance-wheel/:id/delete',
        component: BalanceWheelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.balanceWheel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
