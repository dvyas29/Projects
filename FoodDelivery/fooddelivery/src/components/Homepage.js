import React from 'react';
import styles from './Homepage.module.css';
import subway from '../images/sub.png';
import mcd from '../images/mcd.png';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom';
import NavBar from './NavBar';


export default function Homepage() {

    return (
        <div>
            <NavBar />
            <div className='subway'>
                <Link to='/subway'>
                    <img src={subway} alt='subway.png' thumbnail className={styles.div1} />
                </Link>
            </div>
            <div className='mcd'>
                <Link to='/mcd'>
                    <img src={mcd} alt='mcd.jpg' thumbnail className={styles.div1} />
                </Link>
            </div><br /> <br /> <br />

            <h3 className={styles.container}>Click on the restaraunt you want ! </h3>
        </div>
    )
}
