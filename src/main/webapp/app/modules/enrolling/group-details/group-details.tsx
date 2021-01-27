import './group-details.scss';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { NavLink, RouteComponentProps } from 'react-router-dom';
import axios from 'axios';
import log from 'app/config/log';
import ScheduleElement from 'app/shared/model/domain/dto/schedule-element';
import Typography from '@material-ui/core/Typography/Typography';
import { DataGrid, ValueFormatterParams, CellClassParams } from '@material-ui/data-grid';
import { Translate, translate } from 'react-jhipster';
import GroupData from './group-data';
import mapScheduleElementToGroupData from './group-data-mapper';
import moment from 'moment';
import { APP_DATE_FORMAT_DOT } from 'app/config/constants';
import { toast } from 'react-toastify';
import IconButton from '@material-ui/core/IconButton/IconButton';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';

export interface MatchParams {
  groupCode: string;
}

export interface IGroupDetailsState {
  data: Array<GroupData>;
  isOk: boolean;
}

class GroupDetails extends Component<RouteComponentProps<MatchParams>, IGroupDetailsState> {
  constructor(props: RouteComponentProps<MatchParams>) {
    super(props);
    this.state = { data: [], isOk: false };
  }

  getColumns() {
    return [
      {
        field: 'startDate',
        headerName: translate('groupDetails.column.startDate'),
        valueFormatter: (params: ValueFormatterParams) => moment(params.value as Date).format(APP_DATE_FORMAT_DOT),
        cellClassName: (params: CellClassParams) => (moment(params.value as Date).isBefore(moment()) ? 'date-before' : 'date-after'),
        flex: 1,
      },
      {
        field: 'endDate',
        headerName: translate('groupDetails.column.endDate'),
        valueFormatter: (params: ValueFormatterParams) => moment(params.value as Date).format(APP_DATE_FORMAT_DOT),
        cellClassName: (params: CellClassParams) => (moment(params.value as Date).isBefore(moment()) ? 'date-before' : 'date-after'),
        flex: 1,
      },
      { field: 'building', headerName: translate('groupDetails.column.building'), flex: 1 },
      { field: 'room', headerName: translate('groupDetails.column.room'), flex: 1 },
      { field: 'lecturer', headerName: translate('groupDetails.column.lecturer'), flex: 1 },
    ];
  }

  componentDidMount() {
    const {
      match: { params },
    } = this.props;

    axios
      .get<Array<ScheduleElement>>('/api/class-schedules-details/' + params.groupCode)
      .then(r => {
        const data = r.data.map(element => mapScheduleElementToGroupData(element));
        this.setState({ data, isOk: true });
      })
      .catch(e => {
        log.error(e);
        toast.error('ClassGroup containing this code does not exists!');
        this.setState({ isOk: false });
      });
  }

  render() {
    const {
      match: { params },
    } = this.props;
    const { isOk, data } = this.state;
    return isOk ? (
      <>
        <IconButton onClick={() => this.props.history.goBack()}>
          <ArrowBackIcon />
        </IconButton>
        <Typography style={{ marginBottom: '10px' }} variant="h4" component="h4" align="center">
          <Translate contentKey={'groupDetails.header'}></Translate>
          <span>{' ' + params.groupCode}</span>
        </Typography>

        <div style={{ height: '700px' }}>
          <DataGrid
            rows={data}
            columns={this.getColumns()}
            localeText={translate('dataGrid')}
            disableSelectionOnClick={true}
            hideFooter={true}
          />
        </div>
      </>
    ) : null;
  }
}

const mapStateToProps = () => ({});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(GroupDetails);
