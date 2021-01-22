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

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function Row(props) {
    const { row, onSelected } = props;
    const [open, setOpen] = React.useState(false);
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
                <TableCell align="right">{`${row.rightStartDate.toLocaleDateString()} ${row.rightStartDate.toLocaleTimeString()}`}</TableCell>
                <TableCell>
                    <IconButton size="medium" onClick={() => onSelected(row)}>
                        <KeyboardArrowRightIcon />
                    </IconButton>
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Typography variant="h6" gutterBottom component="div">
                                Bloki
                  </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="right">Początek</TableCell>
                                        <TableCell align="right">Koniec</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {row.enrollmentUnits.map((unit: EnrollmentUnit) => (
                                        <TableRow key={unit.id}>
                                            <TableCell component="th" scope="row" align='right'>{`${new Date().toLocaleDateString()} ${new Date(unit.startDate).toLocaleTimeString()}`}</TableCell>
                                            <TableCell component="th" scope="row" align='right'>{`${new Date(unit.endDate).toLocaleDateString()} ${new Date(unit.endDate).toLocaleTimeString()}`}</TableCell>
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
            }),
        ).isRequired,
    }).isRequired,
};

export default Row;