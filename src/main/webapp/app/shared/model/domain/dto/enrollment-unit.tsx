export default class EnrollmentUnit {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;

  constructor(id: number, startDate: Date, endDate: Date) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
  }
};