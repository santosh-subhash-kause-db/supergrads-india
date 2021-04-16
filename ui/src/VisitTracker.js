import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import CircularProgress from '@material-ui/core/CircularProgress';
import {config} from './constants';

const useStyles = makeStyles({
  root: {
    minWidth: 275,
  },
  bullet: {
    display: 'inline-block',
    margin: '0 2px',
    transform: 'scale(0.8)',
  },
  title: {
    fontSize: 25,
  },
  pos: {
    marginBottom: 12,
  },
});

export default function VisitTracker() {
  const classes = useStyles();
  const [loading, setLoading] = React.useState(true);
  const [visitCount, setVisitCount] = React.useState(0);

  

  React.useEffect( () => {
   
    const addMyVisit = async () => { 
      
       await fetch(config['API_URL'] + '/increment', {
          headers: {'Access-Control-Allow-Origin':'*'},
          method : 'post'
        })
        //.then(response => response.json())
        //.then(json => console.log(json))    
        .catch(err => console.log('Request Failed', err));
    }

      
    const getVisitCount = async () => {
        const response = await fetch(config['API_URL'] + '/count', {
          headers: {'Access-Control-Allow-Origin':'*'}
        });
        const json = await response.json();
        setLoading(false);
        setVisitCount(Number(json));
      }

    
    addMyVisit();   //update database adding my current visit to counter

    const interval = setInterval(() => getVisitCount() , 5000); // fetch latest count every 5 seconds

    return () => clearInterval(interval)

  }, []);



  return (
    <Card className={classes.root}>
      <CardContent>
        <Typography className={classes.title} color="textSecondary" gutterBottom>
          Total visits
        </Typography>
        <Typography variant="h5" component="h2">
            {
                loading ? <CircularProgress /> : visitCount
            }
            
        </Typography>
      </CardContent>
    </Card>
  );
}