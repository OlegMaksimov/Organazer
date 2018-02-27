import { BaseEntity } from './../../shared';

export class Category implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public discription?: string,
        public contentment?: number,
        public categoryBalances?: BaseEntity[],
        public categoryTasks?: BaseEntity[],
    ) {
    }
}
