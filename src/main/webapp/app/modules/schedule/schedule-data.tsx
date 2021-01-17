export default class ScheduleData {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;
  public readonly title: string;
  public readonly type: string;

  constructor(id: number, startDate: Date, endDate: Date, title: string, type: string) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.title = title;
    this.type = type;
  }
};