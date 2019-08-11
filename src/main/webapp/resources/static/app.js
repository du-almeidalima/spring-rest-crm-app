"use strict";

document.querySelector('#get-customer').addEventListener('click', async (event) => {

    const response = await fetch('api/customers/1');
    const body = await response.json();

    document.querySelector('.response-box').innerHTML =`
    <pre>
    "id": "${body.id}",
    "firstName" : "${body.firstName}",
    "lastName" : "${body.lastName}",    
    "email" : "${body.email}",
    </pre>
    `;

    console.log(body);
});
