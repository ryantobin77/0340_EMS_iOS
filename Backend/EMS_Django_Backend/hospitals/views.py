from django.shortcuts import render
from django.http import HttpResponse
from django.http import JsonResponse
from . import models

# Create your views here.
def home(request):
    return HttpResponse('<h1>Welcome to the EMS Mobile Backend!</h1>')


def get_hospitals(request):
    hospitals_list = list()
    hospitals = models.Hospital.objects.all()
    for hospital in hospitals:
        specialty_centers = hospital.specialty_center.all()
        specialty_centers_list = list()
        for spec in specialty_centers:
            specialty_centers_list.append(str(spec))

        diversions = hospital.diversions.all()
        diversions_list = list()
        for div in diversions:
            diversions_list.append(str(div))
        next = {
            'name' : hospital.name,
            'street' : hospital.street,
            'city' : hospital.city,
            'state' : hospital.state,
            'zip' : hospital.zip,
            'phone' : hospital.phone,
            'rch' : hospital.rch,
            'ems_region' : hospital.ems_region,
            'specialty_centers' : specialty_centers_list,
            'last_updated' : hospital.last_updated,
            'nedocs_score' : hospital.nedocs_score,
            'diversions' : diversions_list,

        }
        hospitals_list.append(next)
    json_dict = {'hospitals' : hospitals_list}
    return JsonResponse(json_dict)
