export default class RequestOverLimit {
    public readonly id: number;
    public readonly isExamined: boolean;
    public readonly isAccepted: boolean;

    constructor(id: number, isExamined: boolean, isAccepted: boolean) {
      this.id = id;
      this.isExamined = isExamined;
      this.isAccepted = isAccepted;
    };
};
