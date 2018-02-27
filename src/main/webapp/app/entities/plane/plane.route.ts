import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PlaneComponent } from './plane.component';
import { PlaneDetailComponent } from './plane-detail.component';
import { PlanePopupComponent } from './plane-dialog.component';
import { PlaneDeletePopupComponent } from './plane-delete-dialog.component';

export const planeRoute: Routes = [
    {
        path: 'plane',
        component: PlaneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.plane.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'plane/:id',
        component: PlaneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.plane.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planePopupRoute: Routes = [
    {
        path: 'plane-new',
        component: PlanePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.plane.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plane/:id/edit',
        component: PlanePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.plane.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plane/:id/delete',
        component: PlaneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'organazerApp.plane.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
