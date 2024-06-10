var c=(o,n,t)=>{if(!n.has(o))throw TypeError("Cannot "+t)};var u=(o,n,t)=>(c(o,n,"read from private field"),t?t.call(o):n.get(o)),r=(o,n,t)=>{if(n.has(o))throw
TypeError("Cannot add the same private member more than once");n instanceof WeakSet?n.add(o):n.set(o,t)},b=(o,n,t,e)=>(c(o,n,"write to private field"),e?e.call(o,t):n.set(o,t),t);var d=(o,n,t)=>(c(o,n,"access private method"),t);import{r as f,j as s,t as k,b as w,e as j}from"./assets/toClassName-27ff9a21.js";const E=`:host {
display: inline-block;
}

button {
font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen,
Ubuntu, Cantarell, 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
background: #1b74ff;
border: none;
border-radius: 24px;
padding: 12px 24px;
color: white;
font-weight: bold;
cursor: pointer;
user-select: none;
transition: background 200ms ease-out;
overflow: hidden;
position: relative;
}

button.loading span:first-child {
opacity: 0;
}

button.loading span:last-child {
position: absolute;
inset: 0;
display: flex;
justify-content: center;
align-items: center;
}

button:hover {
background: #2d7eff;
}

button:focus {
background: #196aee;
transition: none;
}

button:disabled {
background: #c5c5c5;
color: #878787;
cursor: default;
}
`;function S({payTool:o,data:n}){const[t,e]=f.useState(!1),[m,h]=f.useState(!1),g=()=>{t||!n||(e(!0),o.init(n).catch(()=>{h(!0),setTimeout(x,2e3)}))},x=()=>{h(!1),e(!1)};return s.jsxs(s.Fragment,{children:[s.jsx("style",{children:E}),s.jsxs("button",{onClick:g,className:k({loading:t}),disabled:!(n!=null&&n.amount),children:[s.jsx("span",{children:"Pay with Paytool"}),t&&s.jsx("span",{children:m?"Error":"Loading..."})]})]})}class y{async init({apiKey:n,...t}){const e=await w.post("transactions/pre-initialization",t,{headers:{"api-key":n}});location.href=`https://paytool-dev.fenige.pl/${e.data.transactionId}`}}var l,a,i,p;class v extends HTMLElement{constructor(){super();r(this,i);r(this,l,new y);r(this,a,void 0);this.attachShadow({mode:"open"})}set data(t){d(this,i,p).call(this,t)}connectedCallback(){b(this,a,j.createRoot(this.shadowRoot)),d(this,i,p).call(this,null)}}l=new WeakMap,a=new WeakMap,i=new WeakSet,p=function(t){u(this,a).render(s.jsx(S,{payTool:u(this,l),data:t}))};customElements.get("paytool-button")||customElements.define("paytool-button",v);window.Paytool=y;