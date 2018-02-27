import { BaseEntity } from './../../shared';

export class BalanceWheel implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public category?: BaseEntity,
    ) {
    }
}
