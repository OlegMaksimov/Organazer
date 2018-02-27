import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { OrganazerTaskModule } from './task/task.module';
import { OrganazerPlaneModule } from './plane/plane.module';
import { OrganazerCategoryModule } from './category/category.module';
import { OrganazerBalanceWheelModule } from './balance-wheel/balance-wheel.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        OrganazerTaskModule,
        OrganazerPlaneModule,
        OrganazerCategoryModule,
        OrganazerBalanceWheelModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrganazerEntityModule {}
