import * as React from 'react';
import Paper from '@material-ui/core/Paper';
import { ViewState } from '@devexpress/dx-react-scheduler';
import {
  Scheduler, WeekView, Appointments, CurrentTimeIndicator, Resources, Toolbar, DateNavigator, TodayButton
} from '@devexpress/dx-react-scheduler-material-ui';
import ScheduleData from './schedule-data';

interface IScheduleState {
  data: Array<ScheduleData>;
  resources: Array<any>;
  currentDate: string;
}

export class Schedule extends React.PureComponent<IScheduleState> {
  state: IScheduleState;
  currentDateChange: (currentDate: any) => void;

  constructor(props) {
    super(props);

    this.state = {
      data: [
        new ScheduleData(1, new Date('2018-10-31T07:30'), new Date('2018-10-31T09:00'), 'BAZY', 'labs'),
        new ScheduleData(2, new Date('2018-10-31T09:15'), new Date('2018-10-31T11:00'), 'TEŻ BAZY ALE NA CZERWONO', 'lecture'),
      ],
      resources: [{
        fieldName: 'type',
        title: 'Type',
        instances: [
          { id: 'lecture', text: 'Lecture', color: '#EC407A' },
          { id: 'labs', text: 'Labs', color: '#7E57C2' },
        ],
      }],
      currentDate: '2018-10-31',
    };
    this.currentDateChange = (currentDate) => { this.setState({ currentDate }); };
  }

  componentDidMount() {
  }

  render() {
    const { data, resources, currentDate } = this.state;

    return (
      <Paper>
        <Scheduler
					data={data}
					height={800}
        >
          <ViewState
            currentDate={currentDate}
            onCurrentDateChange={this.currentDateChange}
          />
          <WeekView
            startDayHour={7.5}
            endDayHour={20.5}
            excludedDays={[0,6]}
          />
  
          <Appointments />

					<CurrentTimeIndicator
              shadePreviousCells={true}
              shadePreviousAppointments={true}
              updateInterval={10000}
            />

          <Resources
            data={resources}
          />
          <Toolbar />
          <DateNavigator />
          <TodayButton />
        </Scheduler>
      </Paper>
    );
  }
}

export default Schedule;
