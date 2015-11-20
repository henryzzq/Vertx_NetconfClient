$(function(){ // on dom ready

$('#cy').cytoscape({
  style: cytoscape.stylesheet()
    .selector('node')
      .css({
        'content': 'data(name)',
        'text-valign': 'center',
        'color': 'white',
        'text-outline-width': 2,
        'text-outline-color': '#888'
      })
    .selector('edge')
      .css({
        'target-arrow-shape': 'triangle'
      })
    .selector(':selected')
      .css({
        'background-color': 'black',
        'line-color': 'black',
        'target-arrow-color': 'black',
        'source-arrow-color': 'black'
      })
    .selector('.faded')
      .css({
        'opacity': 0.25,
        'text-opacity': 0
      }),
  
  elements: 
  {"nodes":[{"data":{"name":"VMS-VMS-Lulu-cloudvpn-CSR-aio1-esc-01","id":"VMS-VMS-Lulu-cloudvpn-CSR-aio1-esc-01","properties":"{gvtype\u003dvRouter, gid\u003dVMS-VMS-Lulu-cloudvpn-CSR-aio1-esc-01}"}},{"data":{"name":"ACME","id":"ACME","properties":"{gvtype\u003dtenant, gid\u003dACME}"}},{"data":{"name":"Wingstop","id":"Wingstop","properties":"{gvtype\u003dtenant, gid\u003dWingstop}"}},{"data":{"name":"VMS-VMS-ACME-cloudvpn-IPS_Sensor-aio1-esc-01","id":"VMS-VMS-ACME-cloudvpn-IPS_Sensor-aio1-esc-01","properties":"{gvtype\u003dvIPS_Sensor, adress\u003d10.118.1.35, gid\u003dVMS-VMS-ACME-cloudvpn-IPS_Sensor-aio1-esc-01}"}},{"data":{"name":"CPE-179d74dd-818b-4ff5-b724-1f379a061e64","id":"CPE-179d74dd-818b-4ff5-b724-1f379a061e64","properties":"{gvtype\u003dcpe, adress\u003d10.254.0.3, gid\u003dCPE-179d74dd-818b-4ff5-b724-1f379a061e64, serial\u003d101, allocates\u003d[{prefix_size\u003d24, ip_type\u003dipv4}]}"}},{"data":{"name":"VMS-VMS-ACME-cloudvpn-WSA-aio1-esc-01","id":"VMS-VMS-ACME-cloudvpn-WSA-aio1-esc-01","properties":"{gvtype\u003dvWSA, adress\u003d10.118.1.33, gid\u003dVMS-VMS-ACME-cloudvpn-WSA-aio1-esc-01}"}},{"data":{"name":"VMS-ACME-cloudvpn","id":"VMS-ACME-cloudvpn","properties":"{gvtype\u003dservice, gid\u003dVMS-ACME-cloudvpn}"}},{"data":{"name":"CPE-fa17745f-248b-449c-89b7-dae2bd50187b","id":"CPE-fa17745f-248b-449c-89b7-dae2bd50187b","properties":"{gvtype\u003dcpe, adress\u003d10.254.0.8, gid\u003dCPE-fa17745f-248b-449c-89b7-dae2bd50187b, serial\u003d108, allocates\u003d[{prefix_size\u003d24, ip_type\u003dipv4}]}"}},{"data":{"name":"CPE-29bcf206-3160-4635-9cb3-4fa2691436f1","id":"CPE-29bcf206-3160-4635-9cb3-4fa2691436f1","properties":"{gvtype\u003dcpe, gid\u003dCPE-29bcf206-3160-4635-9cb3-4fa2691436f1, allocates\u003d[{prefix_size\u003d24, ip_type\u003dipv4}]}"}},{"data":{"name":"VMS-Lulu-cloudvpn","id":"VMS-Lulu-cloudvpn","properties":"{gvtype\u003dservice, gid\u003dVMS-Lulu-cloudvpn}"}},{"data":{"name":"CPE-c944d317-b341-41f4-8075-89baf7ffe457","id":"CPE-c944d317-b341-41f4-8075-89baf7ffe457","properties":"{gvtype\u003dcpe, adress\u003d10.254.0.4, gid\u003dCPE-c944d317-b341-41f4-8075-89baf7ffe457, serial\u003d103, allocates\u003d[]}"}},{"data":{"name":"Lulu","id":"Lulu","properties":"{gvtype\u003dtenant, gid\u003dLulu}"}},{"data":{"name":"10.124.21.187","id":"10.124.21.187","properties":"{groot\u003dtrue, gid\u003d10.124.21.187, gversion\u003d0}"}},{"data":{"name":"VMS","id":"VMS","properties":"{gvtype\u003dprovider, gid\u003dVMS}"}},{"data":{"name":"VMS-VMS-ACME-cloudvpn-CSR-aio1-esc-01","id":"VMS-VMS-ACME-cloudvpn-CSR-aio1-esc-01","properties":"{gvtype\u003dvRouter, adress\u003d10.118.1.34, gid\u003dVMS-VMS-ACME-cloudvpn-CSR-aio1-esc-01}"}},{"data":{"name":"VMS-VMS-Wingstop-cloudvpn-CSR-aio1-esc-01","id":"VMS-VMS-Wingstop-cloudvpn-CSR-aio1-esc-01","properties":"{gvtype\u003dvRouter, adress\u003d10.118.1.37, gid\u003dVMS-VMS-Wingstop-cloudvpn-CSR-aio1-esc-01}"}},{"data":{"name":"VMS-VMS-ACME-cloudvpn-ASA-aio1-esc-01","id":"VMS-VMS-ACME-cloudvpn-ASA-aio1-esc-01","properties":"{gvtype\u003dvFirewall, adress\u003d10.118.1.30, gid\u003dVMS-VMS-ACME-cloudvpn-ASA-aio1-esc-01}"}},{"data":{"name":"VMS-Wingstop-cloudvpn","id":"VMS-Wingstop-cloudvpn","properties":"{gvtype\u003dservice, gid\u003dVMS-Wingstop-cloudvpn}"}},{"data":{"name":"VMS-VMS-ACME-cloudvpn-IPS_Manager-aio1-esc-01","id":"VMS-VMS-ACME-cloudvpn-IPS_Manager-aio1-esc-01","properties":"{gvtype\u003dvIPS_Manager, adress\u003d10.118.1.32, gid\u003dVMS-VMS-ACME-cloudvpn-IPS_Manager-aio1-esc-01}"}},{"data":{"name":"CPE-03af6713-c8f5-408c-b514-be148d9d0143","id":"CPE-03af6713-c8f5-408c-b514-be148d9d0143","properties":"{gvtype\u003dcpe, adress\u003d10.254.0.2, gid\u003dCPE-03af6713-c8f5-408c-b514-be148d9d0143, serial\u003d100, allocates\u003d[{prefix_size\u003d24, ip_type\u003dipv4}]}"}}],"edges":[{"data":{"source":"Lulu","properties":"{gid\u003dSTRUCTURE_Lulu_TO_VMS-Lulu-cloudvpn, gtype\u003dINOUT, gin\u003dVMS-Lulu-cloudvpn, gout\u003dLulu}","target":"VMS-Lulu-cloudvpn"}},{"data":{"source":"VMS-ACME-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-ACME-cloudvpn_TO_CPE-179d74dd-818b-4ff5-b724-1f379a061e64, gtype\u003dINOUT, gin\u003dCPE-179d74dd-818b-4ff5-b724-1f379a061e64, gout\u003dVMS-ACME-cloudvpn}","target":"CPE-179d74dd-818b-4ff5-b724-1f379a061e64"}},{"data":{"source":"VMS-ACME-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-ACME-cloudvpn_TO_VMS-VMS-ACME-cloudvpn-WSA-aio1-esc-01, gtype\u003dINOUT, gin\u003dVMS-VMS-ACME-cloudvpn-WSA-aio1-esc-01, gout\u003dVMS-ACME-cloudvpn}","target":"VMS-VMS-ACME-cloudvpn-WSA-aio1-esc-01"}},{"data":{"source":"VMS-ACME-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-ACME-cloudvpn_TO_VMS-VMS-ACME-cloudvpn-ASA-aio1-esc-01, gtype\u003dINOUT, gin\u003dVMS-VMS-ACME-cloudvpn-ASA-aio1-esc-01, gout\u003dVMS-ACME-cloudvpn}","target":"VMS-VMS-ACME-cloudvpn-ASA-aio1-esc-01"}},{"data":{"source":"Wingstop","properties":"{gid\u003dSTRUCTURE_Wingstop_TO_VMS-Wingstop-cloudvpn, gtype\u003dINOUT, gin\u003dVMS-Wingstop-cloudvpn, gout\u003dWingstop}","target":"VMS-Wingstop-cloudvpn"}},{"data":{"source":"VMS","properties":"{gid\u003dSTRUCTURE_VMS_TO_Wingstop, gtype\u003dINOUT, gin\u003dWingstop, gout\u003dVMS}","target":"Wingstop"}},{"data":{"source":"VMS-Wingstop-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-Wingstop-cloudvpn_TO_CPE-29bcf206-3160-4635-9cb3-4fa2691436f1, gtype\u003dINOUT, gin\u003dCPE-29bcf206-3160-4635-9cb3-4fa2691436f1, gout\u003dVMS-Wingstop-cloudvpn}","target":"CPE-29bcf206-3160-4635-9cb3-4fa2691436f1"}},{"data":{"source":"VMS-Wingstop-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-Wingstop-cloudvpn_TO_CPE-fa17745f-248b-449c-89b7-dae2bd50187b, gtype\u003dINOUT, gin\u003dCPE-fa17745f-248b-449c-89b7-dae2bd50187b, gout\u003dVMS-Wingstop-cloudvpn}","target":"CPE-fa17745f-248b-449c-89b7-dae2bd50187b"}},{"data":{"source":"VMS","properties":"{gid\u003dSTRUCTURE_VMS_TO_ACME, gtype\u003dINOUT, gin\u003dACME, gout\u003dVMS}","target":"ACME"}},{"data":{"source":"VMS","properties":"{gid\u003dSTRUCTURE_VMS_TO_Lulu, gtype\u003dINOUT, gin\u003dLulu, gout\u003dVMS}","target":"Lulu"}},{"data":{"source":"VMS-ACME-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-ACME-cloudvpn_TO_VMS-VMS-ACME-cloudvpn-IPS_Manager-aio1-esc-01, gtype\u003dINOUT, gin\u003dVMS-VMS-ACME-cloudvpn-IPS_Manager-aio1-esc-01, gout\u003dVMS-ACME-cloudvpn}","target":"VMS-VMS-ACME-cloudvpn-IPS_Manager-aio1-esc-01"}},{"data":{"source":"VMS-Lulu-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-Lulu-cloudvpn_TO_VMS-VMS-Lulu-cloudvpn-CSR-aio1-esc-01, gtype\u003dINOUT, gin\u003dVMS-VMS-Lulu-cloudvpn-CSR-aio1-esc-01, gout\u003dVMS-Lulu-cloudvpn}","target":"VMS-VMS-Lulu-cloudvpn-CSR-aio1-esc-01"}},{"data":{"source":"10.124.21.187","properties":"{gid\u003dSTRUCTURE_10.124.21.187_TO_VMS, gtype\u003dINOUT, gin\u003dVMS, gout\u003d10.124.21.187}","target":"VMS"}},{"data":{"source":"VMS-Wingstop-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-Wingstop-cloudvpn_TO_VMS-VMS-Wingstop-cloudvpn-CSR-aio1-esc-01, gtype\u003dINOUT, gin\u003dVMS-VMS-Wingstop-cloudvpn-CSR-aio1-esc-01, gout\u003dVMS-Wingstop-cloudvpn}","target":"VMS-VMS-Wingstop-cloudvpn-CSR-aio1-esc-01"}},{"data":{"source":"VMS-ACME-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-ACME-cloudvpn_TO_CPE-03af6713-c8f5-408c-b514-be148d9d0143, gtype\u003dINOUT, gin\u003dCPE-03af6713-c8f5-408c-b514-be148d9d0143, gout\u003dVMS-ACME-cloudvpn}","target":"CPE-03af6713-c8f5-408c-b514-be148d9d0143"}},{"data":{"source":"VMS-Lulu-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-Lulu-cloudvpn_TO_CPE-c944d317-b341-41f4-8075-89baf7ffe457, gtype\u003dINOUT, gin\u003dCPE-c944d317-b341-41f4-8075-89baf7ffe457, gout\u003dVMS-Lulu-cloudvpn}","target":"CPE-c944d317-b341-41f4-8075-89baf7ffe457"}},{"data":{"source":"ACME","properties":"{gid\u003dSTRUCTURE_ACME_TO_VMS-ACME-cloudvpn, gtype\u003dINOUT, gin\u003dVMS-ACME-cloudvpn, gout\u003dACME}","target":"VMS-ACME-cloudvpn"}},{"data":{"source":"VMS-ACME-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-ACME-cloudvpn_TO_VMS-VMS-ACME-cloudvpn-CSR-aio1-esc-01, gtype\u003dINOUT, gin\u003dVMS-VMS-ACME-cloudvpn-CSR-aio1-esc-01, gout\u003dVMS-ACME-cloudvpn}","target":"VMS-VMS-ACME-cloudvpn-CSR-aio1-esc-01"}},{"data":{"source":"VMS-ACME-cloudvpn","properties":"{gid\u003dSTRUCTURE_VMS-ACME-cloudvpn_TO_VMS-VMS-ACME-cloudvpn-IPS_Sensor-aio1-esc-01, gtype\u003dINOUT, gin\u003dVMS-VMS-ACME-cloudvpn-IPS_Sensor-aio1-esc-01, gout\u003dVMS-ACME-cloudvpn}","target":"VMS-VMS-ACME-cloudvpn-IPS_Sensor-aio1-esc-01"}}]}
  ,
  
  layout: {
    name: 'breadthfirst',
    roots: ["10.124.21.187"],
    padding: 10
  },
  
  // on graph initial layout done (could be async depending on layout...)
  ready: function(){
    window.cy = this;
    
    // giddy up...
    
    cy.elements().unselectify();
    
    cy.on('tap', 'node', function(e){
      var node = e.cyTarget; 
      var neighborhood = node.neighborhood().add(node);
      document.getElementById("properties").innerHTML=this.data('properties');
      cy.elements().addClass('faded');
      neighborhood.removeClass('faded');
    });
    
    cy.on('tap', 'edge',  function(e){
        document.getElementById("properties").innerHTML=this.data('properties');
      });
    
    cy.on('tap', function(e){
      if( e.cyTarget === cy ){
        cy.elements().removeClass('faded');
      }
    });
   
  }
});

}); // on dom ready