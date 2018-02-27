import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrganazerSharedModule } from '../../shared';
import {
    PlaneService,
    PlanePopupService,
    PlaneComponent,
    PlaneDetailComponent,
    PlaneDialogComponent,
    PlanePopupComponent,
    PlaneDeletePopupComponent,
    PlaneDeleteDialogComponent,
    planeRoute,
    planePopupRoute,
} from './';

const ENTITY_STATES = [
    ...planeRoute,
    ...planePopupRoute,
];

@NgModule({
    imports: [
        OrganazerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlaneComponent,
        PlaneDetailComponent,
        PlaneDialogComponent,
        PlaneDeleteDialogComponent,
        PlanePopupComponent,
        PlaneDeletePopupComponent,
    ],
    entryComponents: [
        PlaneComponent,
        PlaneDialogComponent,
        PlanePopupComponent,
        PlaneDeleteDialogComponent,
        PlaneDeletePopupComponent,
    ],
    providers: [
        PlaneService,
        PlanePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrganazerPlaneModule {}
