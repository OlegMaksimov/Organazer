import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrganazerSharedModule } from '../../shared';
import {
    BalanceWheelService,
    BalanceWheelPopupService,
    BalanceWheelComponent,
    BalanceWheelDetailComponent,
    BalanceWheelDialogComponent,
    BalanceWheelPopupComponent,
    BalanceWheelDeletePopupComponent,
    BalanceWheelDeleteDialogComponent,
    balanceWheelRoute,
    balanceWheelPopupRoute,
} from './';

const ENTITY_STATES = [
    ...balanceWheelRoute,
    ...balanceWheelPopupRoute,
];

@NgModule({
    imports: [
        OrganazerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BalanceWheelComponent,
        BalanceWheelDetailComponent,
        BalanceWheelDialogComponent,
        BalanceWheelDeleteDialogComponent,
        BalanceWheelPopupComponent,
        BalanceWheelDeletePopupComponent,
    ],
    entryComponents: [
        BalanceWheelComponent,
        BalanceWheelDialogComponent,
        BalanceWheelPopupComponent,
        BalanceWheelDeleteDialogComponent,
        BalanceWheelDeletePopupComponent,
    ],
    providers: [
        BalanceWheelService,
        BalanceWheelPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrganazerBalanceWheelModule {}
