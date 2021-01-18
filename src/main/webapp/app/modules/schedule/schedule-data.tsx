import GroupState from "./group-state";

export default class ScheduleData {
  public readonly id: number;
  public readonly startDate: Date;
  public readonly endDate: Date;
  public readonly title: string;
  public readonly type: string;
  public readonly lecturerTitle: string;
  public readonly lecturerFirstName: string;
  public readonly lecturerSecondName: string;
  public readonly lecturerLastName: string;
  public readonly room: string;
  public readonly building: string;
  public readonly state: GroupState;

  constructor(id: number, startDate: Date, endDate: Date, title: string, type: string,
    lecturerTitle: string, lecturerFirstName: string, lecturerSecondName: string, lecturerLastName: string,
    room: string, building: string,
    state: GroupState) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.title = title;
    this.type = type;
    this.lecturerTitle = lecturerTitle;
    this.lecturerFirstName = lecturerFirstName;
    this.lecturerSecondName = lecturerSecondName;
    this.lecturerLastName = lecturerLastName;
    this.room = room;
    this.building = building;
    this.state = state;
  }
};