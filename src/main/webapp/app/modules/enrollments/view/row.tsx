import './row.scss';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import KeyboardArrowRightIcon from '@material-ui/icons/KeyboardArrowRight';
import EnrollmentUnit from 'app/shared/model/domain/dto/enrollment-unit';
import EnrollmentData from '../enrollment-data';
import { Translate } from 'react-jhipster';
import Button from '@material-ui/core/Button/Button';
import HourglassEmptyIcon from '@material-ui/icons/HourglassEmpty';
import PlayArrowIcon from '@material-ui/icons/PlayArrow';

const useRowStyles = makeStyles({
  root: {
    '& > *': {
      borderBottom: 'unset',
    },
  },
});

interface IRowProps {
  row: EnrollmentData;
  onSelected: (ed: EnrollmentData) => void;
}

function rowStyleClassname(startDate: Date, endDate: Date) {
  const now = new Date();
  if (endDate < now) {
    return 'timebox-ended';
  } else if (startDate <= now) {
    return 'timebox-inprogress';
  }
  return '';
}

function rightsIcon(date: Date) {
  const now = new Date();
  return now < date ? <HourglassEmptyIcon /> : <PlayArrowIcon />;
}

function Row(props: IRowProps) {
  const { row, onSelected } = props;
  const [open, setOpen] = React.useState(true);
  const classes = useRowStyles();

  return (
    <React.Fragment>
      <TableRow className={classes.root}>
        <TableCell>
          <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell align="right">{row.fieldOfStudy}</TableCell>
        <TableCell align="right">{row.semester}</TableCell>
        <TableCell align="right">{row.speciality}</TableCell>
        <TableCell align="right">{`${row.startDate.toLocaleDateString()} ${row.startDate.toLocaleTimeString()}`}</TableCell>
        <TableCell align="right">{`${row.endDate.toLocaleDateString()} ${row.endDate.toLocaleTimeString()}`}</TableCell>
        <TableCell align="right">
          {rightsIcon(row.rightStartDate)}
          {`${row.rightStartDate.toLocaleDateString()} ${row.rightStartDate.toLocaleTimeString()}`}
        </TableCell>
        <TableCell>
          <Button variant="contained" color="primary" onClick={() => onSelected(row)} endIcon={<KeyboardArrowRightIcon />}>
            <Translate contentKey={'enrollments.right.select'}>Select</Translate>
          </Button>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box margin={1}>
              <Typography variant="h6" gutterBottom component="div">
                <Translate contentKey={'enrollments.right.block.header'}>Blocks</Translate>
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell align="right">
                      <Translate contentKey={'enrollments.right.block.start'}>Start</Translate>
                    </TableCell>
                    <TableCell align="right">
                      <Translate contentKey={'enrollments.right.block.end'}>End</Translate>
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.enrollmentUnits.map((unit: EnrollmentUnit) => (
                    <TableRow key={unit.id} className={rowStyleClassname(new Date(unit.startDate), new Date(unit.endDate))}>
                      <TableCell component="th" scope="row" align="right">{`${new Date(unit.startDate).toLocaleDateString()} ${new Date(
                        unit.startDate
                      ).toLocaleTimeString()}`}</TableCell>
                      <TableCell component="th" scope="row" align="right">{`${new Date(unit.endDate).toLocaleDateString()} ${new Date(
                        unit.endDate
                      ).toLocaleTimeString()}`}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

Row.propTypes = {
  row: PropTypes.shape({
    fieldOfStudy: PropTypes.string.isRequired,
    semester: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    speciality: PropTypes.string,
    startDate: PropTypes.any.isRequired,
    endDate: PropTypes.any.isRequired,
    isPreEnrollment: PropTypes.bool.isRequired,
    rightStartDate: PropTypes.any.isRequired,
    enrollmentUnits: PropTypes.arrayOf(
      PropTypes.shape({
        startDate: PropTypes.any.isRequired,
        endDate: PropTypes.any.isRequired,
      })
    ).isRequired,
  }).isRequired,
};

export default Row;
