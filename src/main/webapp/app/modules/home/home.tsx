import './home.scss';

import { connect } from 'react-redux';

import { IRootState } from 'app/shared/reducers';
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Timeline from '@material-ui/lab/Timeline';
import TimelineItem from '@material-ui/lab/TimelineItem';
import TimelineSeparator from '@material-ui/lab/TimelineSeparator';
import TimelineConnector from '@material-ui/lab/TimelineConnector';
import TimelineContent from '@material-ui/lab/TimelineContent';
import TimelineOppositeContent from '@material-ui/lab/TimelineOppositeContent';
import TimelineDot from '@material-ui/lab/TimelineDot';
import SearchIcon from '@material-ui/icons/Search';
import LaptopMacIcon from '@material-ui/icons/LaptopMac';
import PersonAddIcon from '@material-ui/icons/PersonAdd';
import LiveHelpIcon from '@material-ui/icons/LiveHelp';
import RepeatIcon from '@material-ui/icons/Repeat';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import { Translate } from 'react-jhipster';
import FilterListIcon from '@material-ui/icons/FilterList';
import blue from '@material-ui/core/colors/blue';

export type IHomeProp = StateProps;

const useStyles = makeStyles(theme => ({
  paper: {
    padding: '6px 16px',
  },
  secondaryTail: {
    backgroundColor: theme.palette.secondary.main,
  },
  top: {
    marginTop: '20px',
  },
}));

export const Home = (props: IHomeProp) => {
  const { account } = props;
  const classes = useStyles();

  return (
    <>
      <Typography variant="h4" component="h4" align="center" className={classes.top}>
        <Translate contentKey={'home.title'}>Welcome to the enrollments system!</Translate>
      </Typography>
      {account && account.login ? (
        <Typography variant="h6" component="h6" align="center">
          <Translate contentKey={'home.logged.message'}>Nice to see you again</Translate><b> {account.firstName} {account.lastName}</b>.
        </Typography>
      ) : null}
      <Typography variant="h6" component="h6" align="center" style={{marginTop: '10px'}}>
        <Translate contentKey={'home.timeLineHeader'}>How will your race for the best groups look like?</Translate>
      </Typography>
      <Timeline align="alternate" className={classes.top}>
        <TimelineItem>
          <TimelineOppositeContent>
            <Typography variant="body2">
              <Translate contentKey={'home.timeLine.start'}>Start</Translate>
            </Typography>
          </TimelineOppositeContent>
          <TimelineSeparator>
            <TimelineDot>
              <SearchIcon />
            </TimelineDot>
            <TimelineConnector />
          </TimelineSeparator>
          <TimelineContent>
            <Paper elevation={3} className={classes.paper}>
              <Typography variant="h6" component="h1">
                <Translate contentKey={'home.timeLine.findCourses'}>Find courses</Translate>
              </Typography>
              <Typography>
                <Translate contentKey={'home.timeLine.findCoursesDescription'}></Translate>
              </Typography>
            </Paper>
          </TimelineContent>
        </TimelineItem>
        <TimelineItem>
          <TimelineSeparator>
            <TimelineDot color="primary">
              <FilterListIcon />
            </TimelineDot>
            <TimelineConnector />
          </TimelineSeparator>
          <TimelineContent>
            <Paper elevation={3} className={classes.paper}>
              <Typography variant="h6" component="h1">
                <Translate contentKey={'home.timeLine.filterGroups'}>Filter groups</Translate>
              </Typography>
              <Typography>
                <Translate contentKey={'home.timeLine.filterGroupsDescription'}></Translate>
              </Typography>
            </Paper>
          </TimelineContent>
        </TimelineItem>
        <TimelineItem>
          <TimelineSeparator>
            <TimelineDot color="primary" variant="outlined">
              <PersonAddIcon />
            </TimelineDot>
            <TimelineConnector />
          </TimelineSeparator>
          <TimelineContent>
            <Paper elevation={3} className={classes.paper}>
              <Typography variant="h6" component="h1">
                <Translate contentKey={'home.timeLine.enroll'}>Enroll</Translate>
              </Typography>
              <Typography>
                <Translate contentKey={'home.timeLine.enrollDescription'}></Translate>
              </Typography>
            </Paper>
          </TimelineContent>
        </TimelineItem>
        <TimelineItem>
          <TimelineSeparator>
            <TimelineDot color="primary">
              <LiveHelpIcon></LiveHelpIcon>
            </TimelineDot>
            <TimelineConnector />
          </TimelineSeparator>
          <TimelineContent>
            <Paper elevation={3} className={classes.paper}>
              <Typography variant="h6" component="h1">
                <Translate contentKey={'home.timeLine.requestOverLimit'}>Request to lecturer for enroll over limit</Translate>
              </Typography>
              <Typography>
                <Translate contentKey={'home.timeLine.requestOverLimitDescription'}></Translate>
              </Typography>
            </Paper>
          </TimelineContent>
        </TimelineItem>
        <TimelineItem>
          <TimelineSeparator>
            <TimelineDot color="secondary">
              <RepeatIcon />
            </TimelineDot>
            <TimelineConnector />
          </TimelineSeparator>
          <TimelineContent>
            <Paper elevation={3} className={classes.paper}>
              <Typography variant="h6" component="h1">
                <Translate contentKey={'home.timeLine.repeat'}>Repeat</Translate>
              </Typography>
              <Typography>
                <Translate contentKey={'home.timeLine.repeatDescription'}></Translate>
              </Typography>
            </Paper>
          </TimelineContent>
        </TimelineItem>
        <TimelineItem>
          <TimelineOppositeContent>
            <Typography variant="body2">
              <Translate contentKey={'home.timeLine.finish'}>Finish</Translate>
            </Typography>
          </TimelineOppositeContent>
          <TimelineSeparator>
            <TimelineDot color="primary">
              <LaptopMacIcon />
            </TimelineDot>
          </TimelineSeparator>
          <TimelineContent>
            <Paper elevation={3} className={classes.paper}>
              <Typography variant="h6" component="h1">
                <Translate contentKey={'home.timeLine.checkSchedule'}>Check your final schedule</Translate>
              </Typography>
              <Typography>
                <Translate contentKey={'home.timeLine.checkScheduleDescription'}></Translate>
              </Typography>
            </Paper>
          </TimelineContent>
        </TimelineItem>
      </Timeline>
    </>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
